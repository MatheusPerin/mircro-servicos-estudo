import User from "../user/model/user.js";

class UserRepository {

    async findById(email) {
        try {
            return await User.findOne({
                where: {id}
            });
        } catch (error) {
            console.error('Error when searching by id', error.message);
            return null;
        }
    }

    async findByEmail(email) {
        try {
            return await User.findOne({
                where: {email}
            });
        } catch (error) {
            console.error('Error when searching by email', error.message);
            return null;
        }
    }

}

export default new UserRepository();