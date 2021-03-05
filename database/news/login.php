<?php 

include_once('koneksi.php');

if (!empty($_POST['user_email']) && !empty($_POST['user_password'])) {

    $user_email = $_POST['user_email'];
    $user_password = md5($_POST['user_password']);



    $query = "SELECT * FROM user WHERE user_email = '$user_email' AND user_password = '$user_password'";


$get = mysqli_query($connect, $query);
$row = array();


if(mysqli_num_rows($get) > 0) {
    $row = mysqli_fetch_assoc($get);
    
    set_response(true, "Login Berhasil", $row);
} else {
    set_response(false, "Login gagal", $row);
}
} else {
    set_response(false, "email, password tidak boleh kosong", $data);
}

function set_response($isSuccess, $message, $data) {

    $result = array(
        'isSuccess' => $isSuccess,
        'message' => $message,
        'data' => $data
    );
    echo json_encode($result);
}
