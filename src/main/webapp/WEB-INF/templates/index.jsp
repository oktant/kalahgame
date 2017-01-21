<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Tables</title>
    <script src="<c:url value="/js/sockjs1.js"/>"></script>
    <script src="<c:url value="/js/bootbox.min.js"/>"></script>
    <script src="<c:url value="/js/stomp2.js"/>"></script>

    <script></script>

</head>
<body style="background-color:#21232a;">
<form action="table" method="Get">
    <input type="hidden" id="username" value="${username}">

    <div style="text-align:center">
        <table>
            <tr>
                <td  rowspan="2" style="background-color: green; width:100px; text-align: center" >
                    <input id="u1" type="text" style="width: 100px; height: 120px; text-align: center;background-color: green;" value="0" disabled>
                </td>
                <td ><input id="a16" style="background-color: green; width:100px; height:60px" type="button" onclick="changeBoardValue(this.id)" value="6"></td>
                <td><input id="a15" style="background-color: green; width:100px; height:60px" type="button" onclick="changeBoardValue(this.id)" value="6"></td>
                <td><input id="a14" style="background-color: green; width:100px; height:60px" type="button" onclick="changeBoardValue(this.id)" value="6"></td>

                <td><input id="a13" style="background-color: green; width:100px; height:60px" type="button" onclick="changeBoardValue(this.id)" value="6"></td>
                <td><input id="a12" style="background-color: green; width:100px; height:60px" type="button" onclick="changeBoardValue(this.id)" value="6"></td>
                <td><input id="a11" style="background-color: green; width:100px; height:60px" type="button" onclick="changeBoardValue(this.id)" value="6"></td>
                <td rowspan="2" style="background-color: #999; width:100px; text-align: center" ><input id="u2" type="text" value="0"   style="width: 100px; height: 120px; text-align: center;" disabled></td>
            </tr>
            <tr>
                <td><input id="b11" style="width:100px; height:60px" type="button" onclick="changeBoardValue(this.id)" value="6"></td>
                <td><input id="b12" style="width:100px; height:60px" type="button" onclick="changeBoardValue(this.id)"  value="6"></td>
                <td><input id="b13" style="width:100px; height:60px" type="button" onclick="changeBoardValue(this.id)" value="6"></td>

                <td><input id="b14" style="width:100px; height:60px" type="button" onclick="changeBoardValue(this.id)" value="6"></td>
                <td><input id="b15" style="width:100px; height:60px" type="button" onclick="changeBoardValue(this.id)" value="6"></td>
                <td><input id="b16" style="width:100px; height:60px" type="button" onclick="changeBoardValue(this.id)" value="6"></td>
            </tr>
        </table>


    </div>
    <input type="hidden" id="turnStatus" value="false">
    <div>
        <input type="button"  onclick="disconnect()" value="Exit">
    </div>
    <div id="messageShow" style="width: 400px; float: left;">
        <p id="commingMessages" style="color:white"></p>
    </div>
</form>
</body>

<script>
    function changeBoardValue(){

    }
</script>


<script>
    var stompClient=null;
    var username=$("#username").val();
    var username1=document.getElementById("username").value;

    $(document).ready(function(){
        var cn=new SockJS("/move");
        stompClient=Stomp.over(cn);
        stompClient.connect({'client-id': username}, function(frame){

            stompClient.subscribe('/topic/startGame', function (event) {


                var events = document.getElementById('messageShow');
                var p = document.createElement('p');

                p.appendChild(document.createTextNode(event.body));
                p.style.color="white";

                events.insertBefore(p, events.firstChild);
            });
            stompClient.subscribe('/topic/deleteUser', function(event){
                bootbox.alert(event.body);
                bootbox.alert({
                    message: event.body,
                    callback: function () {
                        window.location.replace("http://localhost:8090/");
                    }
                })

            });


            stompClient.subscribe('/topic/game', function(event){

                var mapOfBoard = JSON.parse(event.body);
                for (var i in mapOfBoard){
                    if(mapOfBoard[i]==-1){
                        bootbox.alert({
                            message: i +" wins",
                            callback: function () {
                                window.location.replace("http://localhost:8090/");
                            }
                        })
                    }
                    $("#"+i).val(mapOfBoard[i]);
                }


            });

            stompClient.send("/app/joinGame", {}, "usernameJoined");

        });

    });
    function disconnect(){
        onClose();
    }
    function onClose(){

        if(stompClient!=null){
            stompClient.send("/app/deleteUser", {}, username);
            stompClient.disconnect();

            window.location.replace("http://localhost:8090/");



        }
    }

    window.addEventListener("beforeunload", function(e){
        onClose();
    }, false);

    function changeBoardValue(clicked_id, valueOfButton){
       stompClient.send("/app/updateBoard", {}, JSON.stringify({"player":username,
           "stepId": clicked_id}));
        stompClient.send("/app/joinGame", {}, "usernameJoined");
    }

</script>
</html>
