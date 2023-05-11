$(document).ready(function (){
    $('.eBtn, table .eBtn').on('click',function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text();

        $.get(href,function (user){

            $('.myForm #name').val(user.name);

        });

        $('.myForm #exampleModal').modal();

    });
});