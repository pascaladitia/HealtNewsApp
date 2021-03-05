<?php

include_once('koneksi.php');

if (!empty($_POST['news_title']) && !empty($_POST['news_desk']) 
    && !empty($_POST['news_author']) && !empty($_POST['news_image'])) {

    $news_title = $_POST['news_title'];
    $news_desk = $_POST['news_desk'];
    $news_author = $_POST['news_author'];
    $user_id = $_POST['user_id'];
    $news_image = $_POST['news_image'];

    $query = "INSERT INTO news(news_title, news_desk, news_author, user_id, news_image) 
        VALUES ('$news_title','$news_desk','$news_author','$user_id','$news_image')";

    $insert = mysqli_query($connect, $query);

    if($insert) {
        set_response(true, "Success insert data");
    } else {
        set_response(false, "Failed insert data");
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
