import express from 'express'
import User from '../model/User.js'
import {registerVal, loginVal, tokenVal} from '../validation.js'
import bcrypt from 'bcrypt'
import jwt from 'jsonwebtoken'

const router = express.Router()

router.post('/register', async (req, res) => {
    
    //Validate Data
    const {error} = registerVal(req.body)
    if(error) return res.status(403).send(error.message)

    //Check if email exists
    const emailExist = await User.findOne({ email: req.body.email })
    if(emailExist) return res.status(400).send("Email already exists.")

    //check if phone exists
    const phoneExist = await User.findOne({ phone: req.body.phone})
    if(phoneExist) return res.status(400).send("Phone number already exists.")

    //Hash password
    const hashedPassword = await hashPassword(req.body.password)

    //Register
    const user = new User({
        username: req.body.username,
        email: req.body.email,
        phone: req.body.phone,
        profile_link: req.body.profile_link,
        password: hashedPassword
    })
    try{
        const savedUser = await user.save()
        //res.setHeader('Content-Type', 'application/json')
        res.status(200).json({message: "Registered successfully", error: null}).send()
        console.log("User saved " + user.username)
    } catch(error){
        res.status(404).send(error)
    }
})

router.post('/login', async (req, res) =>{
    //validate data
    const {error} = loginVal(req.body)
    if(error) return res.send(error.message).status(400)

    //Check if email exists
    const user = await User.findOne({ email: req.body.email })
    if(!user) return res.status(400).send("Email does not exist.")

    //password is correct
    const isPasswordValid = await bcrypt.compare(req.body.password, user.password)
    if(!isPasswordValid) return res.status(400).send("Invalid password.") 

    //Create and assign token
    const token = jwt.sign({_id: user._id}, process.env.TOKEN_SECRET)

    res.json({token: token, message: "Login successful"}).status(200).send()
    console.log("Login successful")
})

router.post('/info', async (req, res) =>{
    //validate data
    const {error} = tokenVal(req.body)
    if(error) return res.send(error.message).status(400)

    const token = req.body.token
    const decoded = null

    //Decode token
    try {
        decoded = jwt.verify(token, process.env.TOKEN_SECRET )
    } catch(error){
        return res.status(404).json({message: "Unauthorized"}).send()
    }

    //Find user with Id
    var userId = decoded._id
    User.findOne({_id: userId}).then(function(user){
        return res.json(user).status(200).send()
    })

    return res.send(500)
})
async function hashPassword(password){
    const salt = await bcrypt.genSalt(10)
    const hashPassword = await bcrypt.hash(password, salt)

    return hashPassword
}

export default router