function handleFormSubmit(event) {
    event.preventDefault(); 

    const email = document.getElementById("email").value

    document.cookie = `user_email=${encodeURIComponent(email)}; path=/; samesite=Strict`;
}