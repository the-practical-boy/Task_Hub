package com.example.taskhub.TaskTrek.Firebase

import android.app.Activity
import android.util.Log
import com.example.taskhub.TaskTrek.activities.MainActivity
import com.example.taskhub.TaskTrek.activities.MyProfileActivity
import com.example.taskhub.TaskTrek.activities.SignInActivity
import com.example.taskhub.TaskTrek.activities.SignUpActivity
import com.example.taskhub.TaskTrek.models.Users
import com.example.taskhub.TaskTrek.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
class FirestoreClass {

    private val mFireStore=FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, userInfo: com.example.taskhub.TaskTrek.models.Users) {
        mFireStore.collection(Constants.USER ).document(
            getCurrentuserID()
        ).set(userInfo, SetOptions.merge()).addOnSuccessListener {
            activity.userRegisteredSuccess()
        }.addOnFailureListener {
            e->
            Log.e(activity.javaClass.simpleName,"Error writing document")
        }
    }

    fun loadUserData(activity: Activity){
        mFireStore.collection(Constants.USER).document(
            getCurrentuserID()
        ).get().addOnSuccessListener {document->
            var loggedInUser=document.toObject(Users::class.java)


            when(activity){
                is SignInActivity->
                    if (loggedInUser != null) {
                        activity.signInSuccess(loggedInUser)
                    }
                is MainActivity->{
                    if (loggedInUser != null) {
                        activity.updateNavigationUserDetails(loggedInUser)
                    }
                }

                is MyProfileActivity->{
                    if (loggedInUser != null) {
                        activity.setUserDetailsUI(loggedInUser)
                    }
                }
            }


        }.addOnFailureListener {
                e->
            Log.e("SignInUser","Error writing document")
                when(activity){
                    is SignInActivity ->{
                        activity.hideProgressDialog()
                    }
                    is MainActivity->{
                        activity.hideProgressDialog()
                    }



                }

                }

    }

    fun getCurrentuserID():String{

        val currentUser=FirebaseAuth.getInstance().currentUser

        var currentUserID=""
        if(currentUser!=null){
            currentUserID=currentUser.uid
        }


        return currentUserID




    }





}