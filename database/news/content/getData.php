<?php

include_once('koneksi.php');
if (!empty($_GET['news_id'])){
    
    $news_id = $_GET['news_id'];
    $query = "SELECT * FROM news WHERE news_id * '$news_id'";

} else {
    $query = "SELECT * FROM news";
}
$get = mysqli_query($connect, $query);

$data = array();
if (mysqli_num_rows($get) > 0) {
    
    while ($row = mysqli_fetch_assoc($get)) {
        $data[] = $row;
    }
    set_response(true, "Data ditemukan", $data);
} else {
    set_response(false, "Data news tidak ditemukan", $data);
}

function set_response($isSuccess, $message, $data) {

    $result = array(
        'isSuccess' => $isSuccess,
        'message' => $message,
        'data' => $data
    );
    echo json_encode($result);
}
