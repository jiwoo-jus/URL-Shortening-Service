/*!
* Start Bootstrap - Coming Soon v6.0.5 (https://startbootstrap.com/theme/coming-soon)
* Copyright 2013-2021 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-coming-soon/blob/master/LICENSE)
*/
window.addEventListener("load", function () {
    document.getElementById('contactForm').addEventListener("submit", function (e) {
        e.preventDefault(); // before the code
        /* do what you want with the form */
        const longUrl = e.target.elements[0].value

        // send api request
        // https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch
        // fetch('http://example.com/movies.json')
        // .then(response => response.json())
        // .then(data => console.log(data));

        handleResponse('http://localhost:8088/b', 1)

        // if error -> put this in catch
        // handleResponseError()
    })
});


const handleResponse = (shortUrl, requestCount) => {
    const requestCountElement = document.getElementById('requestCount');
    const shortUrlElement = document.getElementById('shortUrl');
    requestCountElement.innerText = `Request Number: ${requestCount}`
    shortUrlElement.innerText = `Shortened URL: ${shortUrl}`
    copyToClipboard(shortUrl)
}

const handleResponseError = () => {
    const errorElement = document.getElementById('submitErrorMessage')
    errorElement.innerText = 'Error sending message'
}

function copyToClipboard(text) {
    if (navigator && 'clipboard' in navigator) {
        navigator.clipboard.writeText(text);
    }
}