import mongoose from "mongoose"

const userSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true,
        max:255
    }, email: {
        type:String,
        required: true,
        max: 255
    }, phone:{
        type: String,
        required: true,
        max: 12,
        min: 9
    }, profile_link: {
        type: String,
        required: false
    }, password: {
        type: String,
        required: true,
        max: 255
    }, created_date: {
        type: Date,
        default: Date.now
    }, updated_date: {
        type: Date,
        default: Date.now
    }
})

export default mongoose.model('User', userSchema)