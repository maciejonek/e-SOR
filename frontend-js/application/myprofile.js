document.addEventListener("DOMContentLoaded", function () {
    const profileSection = document.getElementById("profileSection");

    // Pobieranie danych profilu użytkownika
    fetch("http://localhost:8080/my-profile", {
        method: "GET",
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}` // Użycie tokenu JWT
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to fetch profile data. Status: " + response.status);
        }
        return response.json();
    })
    .then(data => {
        // Wyświetlanie danych w HTML
        populateProfileData(data);
    })
    .catch(error => {
        console.error("Error:", error);
        profileSection.innerHTML = `<p class="error">Failed to load profile data.</p>`;
    });
});

// Funkcja do wypełniania danych profilu użytkownika
function populateProfileData(profileData) {
    document.getElementById("name").textContent = `${profileData.name} ${profileData.surname}`;
    document.getElementById("email").textContent = profileData.email;
    document.getElementById("username").textContent = profileData.username;
    document.getElementById("phone_number").textContent = profileData.phone_number || "N/A";
    document.getElementById("dob").textContent = profileData.dob || "N/A";
    document.getElementById("role").textContent = profileData.role;

    // Wypełnianie historii medycznej
    const medicalHistoryDiv = document.getElementById("medicalHistory");
    if (profileData.medical_history && profileData.medical_history.length > 0) {
        profileData.medical_history.forEach(doc => {
            const docDiv = document.createElement("div");
            docDiv.innerHTML = `
                <p><strong>Document ID:</strong> ${doc.document_id}</p>
                <p><strong>Type:</strong> ${doc.document_type}</p>
                <p><strong>Path:</strong> <a href="${doc.document_path}" target="_blank">Download</a></p>
            `;
            medicalHistoryDiv.appendChild(docDiv);
        });
    } else {
        medicalHistoryDiv.innerHTML = "<p>No medical history available.</p>";
    }

    // Wypełnianie informacji o placówce
    if (profileData.facility) {
        document.getElementById("facility").classList.remove("hidden");
        document.getElementById("facility_name").textContent = profileData.facility.facility_name;
        document.getElementById("facility_phone").textContent = profileData.facility.phone_number;
        document.getElementById("facility_address").textContent = `
            ${profileData.facility.address.street} ${profileData.facility.address.number}, 
            ${profileData.facility.address.city}, 
            ${profileData.facility.address.state}, 
            ${profileData.facility.address.zip_code}, 
            ${profileData.facility.address.country}
        `;
    }

    // Wypełnianie tokenów potwierdzających
    const tokensDiv = document.getElementById("confirmationTokens");
    if (profileData.confirmation_tokens && profileData.confirmation_tokens.length > 0) {
        profileData.confirmation_tokens.forEach(token => {
            const tokenDiv = document.createElement("div");
            tokenDiv.innerHTML = `
                <p><strong>Token:</strong> ${token.token}</p>
                <p><strong>Created At:</strong> ${token.created_at}</p>
                <p><strong>Expires At:</strong> ${token.expires_at}</p>
                <p><strong>Confirmed At:</strong> ${token.confirmed_at || "Not confirmed"}</p>
            `;
            tokensDiv.appendChild(tokenDiv);
        });
    } else {
        tokensDiv.innerHTML = "<p>No confirmation tokens found.</p>";
    }
}
