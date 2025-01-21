document.addEventListener("DOMContentLoaded", function () {
    const profileSection = document.getElementById("profileSection");
 
    fetch("http://localhost:8080/myProfile", {
        method: "GET",
        credentials: "include"
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to fetch profile data. Status: " + response.status);
        }
        return response.json();
    })
    .then(data => {
        populateProfileData(data);
    })
    .catch(error => {
        console.error("Error:", error);
        profileSection.innerHTML = `<p class="error">Failed to load profile data.</p>`;
    });
});

function populateProfileData(data) {
    document.getElementById("name").textContent = data.name;
    document.getElementById("surname").textContent = data.surname;
    document.getElementById("date_of_birth").textContent = data.dateOfBirth;
    document.getElementById("phone_number").textContent = data.phoneNumber;
    document.getElementById("email").textContent = data.email;
    document.getElementById("pesel_number").textContent = data.peselNumber;
}
