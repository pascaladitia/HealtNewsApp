<?php

include_once('koneksi.php');

if (!empty($_POST['news_id']) && !empty($_POST['news_title']) 
&& !empty($_POST['news_desk']) && !empty($_POST['news_author']) && !empty($_POST['news_image'])) {

    $news_id = $_POST['news_id'];
    $news_title = $_POST['news_title'];
    $news_desk = $_POST['news_desk'];
    $news_author = $_POST['news_author'];
    $user_id = $_POST['user_id'];
    $news_image = $_POST['news_image'];

    $query = "UPDATE news set news_title = '$news_title',news_desk = '$news_desk',
    news_author = '$news_author',user_id = '$user_id',news_image = '$news_image' WHERE news_id = '$news_id'";

    $update = mysqli_query($connect, $query);

    if($update) {
        set_response(true, "Success update data");
    } else {
        set_response(false, "False update data");
    }
} else {
    set_response(false, "tidak boleh ada yang kosong");
}

function set_response($isSuccess, $message) {
    $result = array(
        'isSuccess' => $isSuccess,
        'message' => $message
    );
    
    echo json_encode($result);
}