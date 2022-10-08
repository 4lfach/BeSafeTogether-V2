import mongoose from "mongoose"

const commentSchema = new mongoose.Schema({
    user_id:{
        type:String,
        required: true
    }, lat:{
        type: Number,
        required: true
    }, lng:{
        type: Number,
        required: true
    }, content:{
        type: String,
        required: true,
        min:20,
        max:255
    }, rating:{
        type: Number,
        required: true
    }, created_date:{
        type: Date,
        default: Date.now
    }
})

export default mongoose.model('Comment', commentSchema)