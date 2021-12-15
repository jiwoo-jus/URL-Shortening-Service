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
            const requestCount = responseJson['requestCount']
            console.log(responseJson)
            handleResponse(shortUrl, requestCount)
        })
        .catch((error) => {
            console.log(error)
        });

})


const handleResponse = (shortUrl, requestCount) => {
    const requestCountElement = document.getElementById('requestCount');
    const shortUrlElement = document.getElementById('shortUrl');
    const errorElement = document.getElementById('submitErrorMessage');
    requestCountElement.innerText = `Request Number: ${requestCount}`
    shortUrlElement.innerText = `Shortened URL: ${shortUrl}`
    errorElement.innerText = ``
}

const handleResponseError = (submitErrorMessage) => {
    const requestCountElement = document.getElementById('requestCount');
    const shortUrlElement = document.getElementById('shortUrl');
    const errorElement = document.getElementById('submitErrorMessage');
    requestCountElement.innerText = ``
    shortUrlElement.innerText = ``
    errorElement.innerText = `${submitErrorMessage}`
}

