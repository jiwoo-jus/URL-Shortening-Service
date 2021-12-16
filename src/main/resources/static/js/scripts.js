/*!
* Start Bootstrap - Coming Soon v6.0.5 (https://startbootstrap.com/theme/coming-soon)
* Copyright 2013-2021 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-coming-soon/blob/master/LICENSE)
*/

window.addEventListener("submit", function (e) {

    document.getElementById('urlForm')
    e.preventDefault();
    const longUrl = e.target.elements[0].value

    fetch("/", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            originalUrl: longUrl
        }),
    })
        .then((response) => {
            if(response.ok){
                return response.json();
            } else {
                    response.text().then(
                    function (result){
                        console.log(result)
                        handleResponseError(result)
                    })
                    throw new Error(response.status.toString())
            }
        })
        .then((responseJson)=>{
            const shortUrl = responseJson['shortUrl']
            const originalUrl = responseJson['originalUrl']
            console.log(responseJson)
            handleResponse(shortUrl, originalUrl)
        })
        .catch((error) => {
            console.log(error)
        });


})


const responseContentElement = document.getElementById('response-content');
const errorElement = document.getElementById('submitErrorMessage');
const shortUrlElement = document.getElementById('shortUrl');
const originalUrlElement = document.getElementById('originalUrl');
const button = document.getElementById("btn-furkan");


const handleResponse = (shortUrl, originalUrl) => {
    responseContentElement.style.display = 'block';
    errorElement.style.display = 'none'
    originalUrlElement.innerText = `${originalUrl}`
    shortUrlElement.innerText = `${shortUrl}`
    button.style.display = 'inline-block'
    copyToClipboard(button, shortUrl)

    shortUrlElement.addEventListener("click", (e) => {
        location.href = `${shortUrl}`
    })
}


const handleResponseError = (submitErrorMessage) => {
    responseContentElement.style.display = 'none';
    errorElement.style.display = 'block';
    errorElement.innerText = `${submitErrorMessage}`
}


const withClipboardAPICopy = (text) => {
    navigator.clipboard.writeText(text).then(
        function () {
            console.log("Async: Copying to clipboard was successful!");
        },
        function (err) {
            console.error("Async: Could not copy text: ", err);
        }
    )
}


function copyToClipboard(button, text) {
    button.addEventListener("click", (e) => {
        button.textContent = "copied";

        setTimeout(() => {
            button.textContent = "copy";
        }, 2000);

        withClipboardAPICopy(text);
    })};