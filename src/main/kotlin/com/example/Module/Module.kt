package com.example.Module

//fun Application.module() {

//    install(Koin) {
//        modules(databaseModule)
//        modules(userModule)
//        modules(questioningModule)
//        modules(friendsModule)
//    }
//
//    install(Authentication) {
//        jwt {
//            verifier(JwtConfig.verifier)
//            realm = "com.example"
//            validate {
//                val id = it.payload.getClaim("id").asInt()
//                val nickname = it.payload.getClaim("nickname").asString()
//                val password = it.payload.getClaim("password").asString()
//                if (nickname != null && password != null) {
//                    UserPrincipal(id, nickname, password)
//                } else {
//                    null
//                }
//            }
//        }
//    }
//
//    routing {
//        val userController: UserController = get()
//        val questioningController: QuestioningController = get()
//        val friendsController: FriendsController = get()
//
//        userController.userRoute(this)
//        questioningController.questioningRoute(this)
//        friendsController.friendsRoute(this)
//    }
//
//    configureHTTP()
//    configureMonitoring()
//    configureSerialization()
//    configureDatabases()
//    configureSockets()
//    configureAdministration()
//    configureRouting()
//}