<?php 
require_once("../codebase/connector/scheduler_connector.php");
$res = new mysqli("localhost", "root", "", "events");

if ($res->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$conn = new SchedulerConnector($res);

$conn->render_table("events","id","start_date,end_date,text");
$conn->close();
?>