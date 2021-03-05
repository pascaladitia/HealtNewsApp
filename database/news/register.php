<?php

include_once('koneksi.php');

if (!empty($_POST['user_email']) && !empty($_POST['user_password'])) {

    $user_email = $_POST['user_email'];
    $user_password = md5($_POST['user_password']);

    $query = "SELECT user_email FROM user WHERE user_email = '$user_email'";
    $get = mysqli_query($connect, $query);

$data = array();

if (mysqli_num_rows($get) > 0) {
    set_response(true, "Email sudah terdaftar", $data);

} else {
    $query = "INSERT INTO user(user_email,user_password) 
    VALUES ('$user_email','$user_password')";

    $insert = mysqli_query($connect, $query);

    if($insert) {
        set_response(true, "Register success");
    } else {
        set_response(false, "Register failed");
    }
}
} else {
    set_response(false, "nama, hp, email & password harus diisi");
}

function set_response($isSuccess, $message) {

    $result = array(
        'isSuccess' => $isSuccess,
        'message' => $message
    );
    echo json_encode($result);
}