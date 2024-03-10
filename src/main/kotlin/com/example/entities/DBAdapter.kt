package entities

import com.example.entities.*
import com.example.util.DatabaseManager

object DBAdapter {
    private val manager: DatabaseManager = DatabaseManager()

//    val createUsersTableRequest = """
//        create table if not exists accounts (
//            id serial primary key,
//            login varchar(256),
//            password int,
//            role int);
//        """.trimIndent()
//
//    val createMealsTableRequest = """
//        create table if not exists menu (
//            id serial primary key,
//            name varchar(256),
//            price int,
//            amount int,
//            cookingTime int);
//        """.trimIndent()
//
//    val createUsersActiveTableRequest = """
//        create table if not exists userActivity (
//            id serial primary key,
//            userId int,
//            typeOfAction varchar(256),
//            time timestamp);
//        """.trimIndent()
//
//    val createIncomeTableRequest = """
//        create table if not exists income (
//            id serial primary key,
//            date date,
//            userId int,
//            sum int);
//        """.trimIndent()


    init {
        val createUsersTableRequest = """
        create table if not exists accounts (
            id serial primary key,
            login varchar(256),
            password varchar(256),
            role int);
        """.trimIndent()

        val createMenuTableRequest = """
        create table if not exists menu (
            id serial primary key,
            name varchar(256),
            price int,
            amount int,
            cookingTime int);
        """.trimIndent()

        val createUsersActiveTableRequest = """
        create table if not exists userActivity (
            id serial primary key,
            userId int,
            typeOfAction varchar(256),
            time varchar(256));
        """.trimIndent()

        val createIncomeTableRequest = """
        create table if not exists income (
            id serial primary key,
            date varchar(256),
            userId int,
            sum int);
        """.trimIndent()

        println(manager.performExecute(createUsersTableRequest))
        println(manager.performExecute(createMenuTableRequest))
        println(manager.performExecute(createUsersActiveTableRequest))
        println(manager.performExecute(createIncomeTableRequest))
    }

    fun addAccount(login: String, password: String, role: Int) {
        val selectUserRequest = """
        select * from accounts
        where login = '$login'
        and password = '$password';
        """.trimIndent()
        val result = manager.performExecuteQuery(selectUserRequest) ?: throw NullPointerException()
        if (result.next()){
            throw ArrayStoreException()
        }
        val insertRequest = """
        insert into accounts (login, password, role)
        values ('$login', '$password', '$role');
        """.trimIndent()
        manager.performExecuteUpdate(insertRequest)
    }

    fun getUser(login: String, password: String): User {
        val selectUserRequest = """
        select * from accounts
        where login = '$login'
        and password = '$password';
        """.trimIndent()
        val result = manager.performExecuteQuery(selectUserRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        if (result.getInt("role") == 1)
            return UserAdmin( result.getInt("id").toUInt(), result.getString("login"), result.getString("password"))
        return UserVisitor(result.getInt("id").toUInt(), result.getString("login"), result.getString("password"))
    }

    fun getUserIdByUsername(login: String) : Int {
        val selectUserRequest = """
        select * from accounts
        where login = '$login';
        """.trimIndent()
        val result = manager.performExecuteQuery(selectUserRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        return result.getInt("id")
    }

    fun getUserByUsername(login: String) : User {
        val selectUserRequest = """
        select * from accounts
        where login = '$login';
        """.trimIndent()
        val result = manager.performExecuteQuery(selectUserRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException("There is no such user")
        }
        if (result.getInt("role") == 1)
            return UserAdmin( result.getInt("id").toUInt(), result.getString("login"), result.getString("password"))
        return UserVisitor(result.getInt("id").toUInt(), result.getString("login"), result.getString("password"))
    }

    fun addMealToMenu(name: String, price: UInt, cookingTime: Int, amount: UInt) {
        val selectByMealNameRequest = """
        select * from menu
        where name = '$name';
        """.trimIndent()
        val result = manager.performExecuteQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (result.next()){
            throw ArrayStoreException()
        }
        val insertRequest = """
        insert into menu (name, price, amount, cookingTime)
        values ('$name', '$price','$amount', '$cookingTime');
        """.trimIndent()
        manager.performExecuteUpdate(insertRequest)
    }

    fun addUserActivity(userid: Int, typeOfAction: String, time: String) {
        val selectByMealNameRequest = """
        select * from accounts
        where id = '$userid';
        """.trimIndent()
        val result = manager.performExecuteQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        val insertRequest = """
        insert into userActivity (userId, typeOfAction, time)
        values ('$userid', '$typeOfAction', '$time');
        """.trimIndent()
        manager.performExecuteUpdate(insertRequest)
    }

    fun getUserActivity(): MutableList<UserActivity>{
        val data = mutableListOf<UserActivity>()
        val selectUserRequest = """
        select * from usersActive;
        """.trimIndent()
        val result = manager.performExecuteQuery(selectUserRequest) ?: throw NullPointerException()
        while (result.next()){
            var activeUser = UserActivity()
            activeUser.userId = result.getInt("userId")
            activeUser.action = result.getString("typeOfAction")
            activeUser.time = result.getString("time")
            data += activeUser
        }
        return data
    }

    fun addSum(userid: Int, date: String, sum: UInt){
        val selectByMealNameRequest = """
        select * from accounts
        where id = '$userid';
        """.trimIndent()
        val result = manager.performExecuteQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        val insertRequest = """
        insert into income (date, userId, sum)
        values ('$date', '$userid','$sum');
        """.trimIndent()
        manager.performExecuteUpdate(insertRequest)
    }

    fun isMealInMenu(name: String): Meal {
        val selectByMealNameRequest = """
        select * from menu
        where name = '$name';
        """.trimIndent()
        val result = manager.performExecuteQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        return Meal(result.getString("name"),result.getInt("cookingTime").toUInt(),result.getInt("price").toUInt(), result.getInt("id"))
    }

    fun getIncomePerDay(date: String): UInt{
        var income : UInt = 0u
        val selectUserRequest = """
        select * from income
        where date = '$date';
        """.trimIndent()
        val result = manager.performExecuteQuery(selectUserRequest) ?: throw NullPointerException()
        while (result.next()){
            income += result.getInt("sum").toUInt()
        }
        return income
    }

    fun getIncome(): UInt{
        var income : UInt = 0u
        val selectUserRequest = """
        select * from income;
        """.trimIndent()
        val result = manager.performExecuteQuery(selectUserRequest) ?: throw NullPointerException()
        while (result.next()){
            income += result.getInt("sum").toUInt()
        }
        return income
    }

    fun takeMealAway(mealId: Int, amount: UInt = 1u){
        val selectByMealNameRequest = """
        select * from menu
        where id = '$mealId';
        """.trimIndent()
        val result = manager.performExecuteQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        if (result.getInt("amount").toUInt() < amount){
            throw ArithmeticException()
        }
        val updateMealAmountValue = """
        UPDATE menu SET amount = amount - '$amount'
        WHERE id = '$mealId';
        """.trimIndent()
        manager.performExecuteUpdate(updateMealAmountValue)
    }

    fun increaseMealAmount(name: String, amount: UInt = 1u){
        val selectByMealNameRequest = """
        select * from menu
        where name = '$name';
        """.trimIndent()
        val result = manager.performExecuteQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        val updateMealAmountValue = """
        UPDATE menu SET amount = amount + '$amount'
        WHERE name = '$name';
        """.trimIndent()
        manager.performExecuteUpdate(updateMealAmountValue)
    }

    fun changeMealPrice(name: String, price: UInt){
        val selectByMealNameRequest = """
        select * from menu
        where name = '$name';
        """.trimIndent()
        val result = manager.performExecuteQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        val updateMealCostValue = """
        UPDATE menu SET price = '$price'
        WHERE name = '$name';
        """.trimIndent()
        manager.performExecuteUpdate(updateMealCostValue)
    }

    fun getMeal(name: String) : Meal {
        val selectByMealNameRequest = """
        select * from menu
        where name = '$name';
        """.trimIndent()
        val result = manager.performExecuteQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        return Meal(result.getString("name"),result.getInt("cookingTime").toUInt(),result.getInt("price").toUInt(), result.getInt("id"))
    }

    fun getMenu() : MutableList<Meal>{
        val selectMealRequest = """
        select * from menu;
        """.trimIndent()
        val result = manager.performExecuteQuery(selectMealRequest) ?: throw NullPointerException()
        val meals = mutableListOf<Meal>()
        while (result.next()){
            meals += Meal(result.getString("name"),result.getInt("cookingTime").toUInt(),result.getInt("price").toUInt(), result.getInt("id"))
        }
        return meals
    }

    fun removeMealFromMenu(name: String){
        val selectByMealNameRequest = """
        select * from menu
        where name = '$name';
        """.trimIndent()
        val result = manager.performExecuteQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        val deleteMealByNameRequest = """
        delete from menu
        where name = '$name'
        """.trimIndent()
        manager.performExecuteUpdate(deleteMealByNameRequest)
    }

}