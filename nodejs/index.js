import express from 'express'
import mongoose from 'mongoose'
import authRoute from './routes/auth.js'
import userUpdateRoute from './routes/userUpdate.js'
import commentRoute from './routes/comment.js'
import dotenv from 'dotenv'

const env = dotenv.config()

const app = express()

//connect to DB
mongoose.connect(process.env.DB_CONNECT,
 () =>{
    console.log('Connected to DB')
})

//middleware
app.use(express.json())
//route middleware
app.use('/api/user', authRoute)
app.use('/api/user/update', userUpdateRoute)
app.use('/api/comment', commentRoute)

app.listen(3000, () =>{
    console.log('Listening on port 3000')
})