import express from 'express'
import verifyToken from './verifyToken.js'
import {commentDataVal} from '../validation.js'
import Comment from '../model/Comment.js'

const router = express.Router()

router.post('/create', verifyToken, async (req, res) =>{

    //Validate data
    const {error} = commentDataVal(req.body)
    if(error) return res.send(error.message).status(400)

    //Find if user's comment exists in selected place
    const commentExist =await Comment.findOne({user_id: req.user._id,lat: req.body.lat,lng: req.body.lng})
    if(commentExist) return res.send('Comment already exists in selected place').status(400)

    const comment = new Comment({
        user_id: req.user._id,
        lat: req.body.lat,
        lng: req.body.lng,
        content: req.body.content,
        rating: req.body.rating
    })

    try{
        await comment.save()
        res.send(comment._id).status(200)
        console.log('comment saved')
    } catch(error){
        res.status(404).send(error)
    }
})

router.delete('/delete', verifyToken, async (req, res) =>{
    try{
        await Comment.findOneAndDelete({user_id: req.user._id, _id:req.body._id})
        res.send("Comment deleted.").status(200)
    } catch(error){
        res.status(400).send(error)
    }
})

export default router