import express from 'express'
import verifyToken from './verifyToken.js'
import {userDataVal} from '../validation.js'
import User from '../model/User.js'

const router = express.Router()

router.get('/', verifyToken, async (req, res) => {
    const users = await User.find
    res.send(req.user)
})

router.put('/profile', verifyToken, async (req, res)=>{
    //validate data
    const {error} = userDataVal(req.body)
    if(error) return res.send(error.message).status(400)

    const filter = {_id: req.user._id}
    User.updateOne(filter, req.body, (err, res) =>{
        if(err) res.send(err).status(400)
        console.log("user updated")
    })

    res.send("User Updated").status(200)
})

export default router