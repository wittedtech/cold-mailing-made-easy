document.querySelector('.file-upload-label').addEventListener('click', function() {
    const fileInput = document.querySelector('#file');
    //fileInput.click();
});

document.querySelector('.upload-button').addEventListener('click', function() {
    const fileInput = document.querySelector('#file');
    if (!fileInput.value) {
        alert('Please select a file to upload.');
    }
});


// Success.html script


// Initialize the countdown timer to 10 seconds
function countdown(){
	let countdown = 10;
	const countdownElement = document.getElementById('countdown');
	
	// Update the countdown every second
	const interval = setInterval(function () {
	    countdown--;  // Decrease the countdown by 1
	    countdownElement.textContent = countdown;  // Update the displayed countdown
	
	    // When the countdown reaches 0, redirect to the main page
	    if (countdown <= 0) {
	        clearInterval(interval);  // Stop the interval timer
	        window.location.href = '/';  // Redirect to the main page
	    }
	}, 1000);
}