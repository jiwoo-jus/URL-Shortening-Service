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
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            originalUrl: longUrl
        }),
    })
        .then((response) => response.json())
        .then((response) => {
            const shortUrl = response['shortUrl']
            const requestCount = response['requestCount']
            handleResponse(shortUrl, requestCount)
            return response;
        })

    // if error -> put this in catch
    // handleResponseError()
    // })
})


const handleResponse = (shortUrl, requestCount) => {
    const requestCountElement = document.getElementById('requestCount');
    const shortUrlElement = document.getElementById('shortUrl');
    requestCountElement.innerText = `Request Number: ${requestCount}`
    shortUrlElement.innerText = `Shortened URL: ${shortUrl}`
}

const handleResponseError = () => {
    const errorElement = document.getElementById('submitErrorMessage')
    errorElement.innerText = 'Error sending message'
}

