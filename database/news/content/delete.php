<?php

include_once('koneksi.php');

if (!empty($_POST['news_id'])) {

    $news_id = $_POST['news_id'];
    
    $query = "DELETE FROM news WHERE news_id = '$news_id'";

    $delete = mysqli_query($connect, $query);

    if($delete) {
        set_response(true, "Success delete data");
    } else {
        set_response(false, "Failed delete data");
    }
} else {
    set_response(false, "news_id harus diisi");
}

function set_response($isSuccess, $message) {
    $result = array(
        'isSuccess' => $isSuccess,
        'message' => $message
    );

    echo json_encode($result);
}