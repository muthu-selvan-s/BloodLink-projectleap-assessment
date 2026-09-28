/* =========================================================
   BLOODLINK - MAIN JAVASCRIPT
========================================================= */


/* =========================================================
   DASHBOARD
========================================================= */

async function loadDashboard() {

    try {

        const response =
            await fetch("/api/statistics/dashboard");

        if (!response.ok) {
            throw new Error("Failed to load dashboard");
        }

        const data =
            await response.json();


        /* =========================
           SUMMARY
        ========================== */

        const totalDonors =
            document.getElementById("totalDonors");

        const availableDonors =
            document.getElementById("availableDonors");

        const unavailableDonors =
            document.getElementById("unavailableDonors");


        if (totalDonors) {
            totalDonors.textContent =
                data.summary.totalDonors;
        }


        if (availableDonors) {
            availableDonors.textContent =
                data.summary.availableDonors;
        }


        if (unavailableDonors) {
            unavailableDonors.textContent =
                data.summary.unavailableDonors;
        }


        /* =========================
           BLOOD GROUPS
        ========================== */

        const bloodGroups =
            document.getElementById("bloodGroups");


        if (bloodGroups) {

            bloodGroups.innerHTML = "";


            for (
                const [group, count]
                of Object.entries(data.bloodGroups)
            ) {

                bloodGroups.innerHTML += `

                    <div class="feature-card">

                        <div class="feature-icon">
                            🩸
                        </div>

                        <h3>
                            ${escapeHtml(group)}
                        </h3>

                        <p>
                            ${count}
                            Available Donors
                        </p>

                    </div>

                `;

            }

        }


        /* =========================
           CITIES
        ========================== */

        const cities =
            document.getElementById("cities");


        if (cities) {

            cities.innerHTML = "";


            for (
                const [city, count]
                of Object.entries(data.cities)
            ) {

                cities.innerHTML += `

                    <div class="feature-card">

                        <div class="feature-icon">
                            📍
                        </div>

                        <h3>
                            ${escapeHtml(city)}
                        </h3>

                        <p>
                            ${count}
                            Available Donors
                        </p>

                    </div>

                `;

            }

        }

    }

    catch (error) {

        console.error(
            "Dashboard error:",
            error
        );

    }

}


/* =========================================================
   LOAD DASHBOARD
========================================================= */

if (
    document.getElementById("totalDonors")
) {

    loadDashboard();

}


/* =========================================================
   LOAD BLOOD GROUPS
========================================================= */

async function loadBloodGroups(selectId) {

    try {

        const response =
            await fetch("/api/blood-groups");


        if (!response.ok) {

            throw new Error(
                "Failed to load blood groups"
            );

        }


        const groups =
            await response.json();


        const select =
            document.getElementById(selectId);


        if (!select) {
            return;
        }


        select.innerHTML = `
            <option value="">
                Select Blood Group
            </option>
        `;


        groups.forEach(
            function (group) {

                const option =
                    document.createElement(
                        "option"
                    );


                option.value =
                    group.id;


                option.textContent =
                    group.bloodGroup;


                select.appendChild(
                    option
                );

            }
        );

    }

    catch (error) {

        console.error(
            "Blood group error:",
            error
        );

    }

}


/* =========================================================
   REGISTER DONOR
========================================================= */

if (
    document.getElementById("donorForm")
) {

    loadBloodGroups("bloodGroup");


    const donorForm =
        document.getElementById("donorForm");


    donorForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            const name =
                document.getElementById(
                    "name"
                ).value.trim();


            const phone =
                document.getElementById(
                    "phone"
                ).value.trim();


            const email =
                document.getElementById(
                    "email"
                ).value.trim();


            const city =
                document.getElementById(
                    "city"
                ).value.trim();


            const bloodGroupId =
                document.getElementById(
                    "bloodGroup"
                ).value;


            const message =
                document.getElementById(
                    "registerMessage"
                );


            if (
                !name ||
                !phone ||
                !email ||
                !city ||
                !bloodGroupId
            ) {

                if (message) {

                    message.textContent =
                        "Please fill in all required fields.";

                    message.style.color =
                        "#c62828";

                }

                return;

            }


            const donorData = {

                name: name,

                phone: phone,

                email: email,

                city: city,

                bloodGroup: {

                    id: Number(
                        bloodGroupId
                    )

                },

                lastDonationDate:
                    null,

                available:
                    true

            };


            try {

                const response =
                    await fetch(
                        "/api/donors",
                        {

                            method:
                                "POST",

                            headers: {

                                "Content-Type":
                                    "application/json"

                            },

                            body:
                                JSON.stringify(
                                    donorData
                                )

                        }
                    );


                if (!response.ok) {

                    const errorText =
                        await response.text();

                    throw new Error(
                        errorText ||
                        "Failed to register donor"
                    );

                }


                await response.json();


                if (message) {

                    message.textContent =
                        "✅ Donor registered successfully!";

                    message.style.color =
                        "#2e7d32";

                }


                donorForm.reset();

            }

            catch (error) {

                console.error(
                    "Registration error:",
                    error
                );


                if (message) {

                    message.textContent =
                        "❌ Failed to register donor. Please try again.";

                    message.style.color =
                        "#c62828";

                }

            }

        }
    );

}


/* =========================================================
   SEARCH DONOR
========================================================= */

if (
    document.getElementById("searchForm")
) {

    loadBloodGroups(
        "searchBloodGroup"
    );


    const searchForm =
        document.getElementById(
            "searchForm"
        );


    searchForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            const bloodGroupSelect =
                document.getElementById(
                    "searchBloodGroup"
                );


            const cityInput =
                document.getElementById(
                    "searchCity"
                );


            const searchMessage =
                document.getElementById(
                    "searchMessage"
                );


            const donorResults =
                document.getElementById(
                    "donorResults"
                );


            if (
                !bloodGroupSelect ||
                !cityInput ||
                !donorResults
            ) {

                console.error(
                    "Search form elements are missing."
                );

                return;

            }


            const city =
                cityInput.value.trim();


            const selectedOption =
                bloodGroupSelect.options[
                    bloodGroupSelect.selectedIndex
                ];


            if (
                !selectedOption ||
                !selectedOption.value
            ) {

                if (searchMessage) {

                    searchMessage.textContent =
                        "Please select a blood group.";

                    searchMessage.style.color =
                        "#c62828";

                }

                return;

            }


            const bloodGroup =
                selectedOption.textContent.trim();


            donorResults.innerHTML =
                "";


            if (searchMessage) {

                searchMessage.textContent =
                    "Searching for available donors...";

                searchMessage.style.color =
                    "#666";

            }


            try {

                const url =
                    "/api/donors/search?bloodGroup="
                    +
                    encodeURIComponent(
                        bloodGroup
                    )
                    +
                    "&city="
                    +
                    encodeURIComponent(
                        city
                    );


                const response =
                    await fetch(url);


                if (!response.ok) {

                    const errorText =
                        await response.text();

                    throw new Error(
                        errorText ||
                        "Search failed"
                    );

                }


                const donors =
                    await response.json();


                /* =========================
                   NO RESULTS
                ========================== */

                if (
                    donors.length === 0
                ) {

                    if (searchMessage) {

                        searchMessage.textContent =
                            "No available donors found.";

                        searchMessage.style.color =
                            "#c62828";

                    }


                    donorResults.innerHTML = `

                        <div class="no-results">

                            🩸 No available donors
                            found for
                            ${escapeHtml(
                                bloodGroup
                            )}
                            in
                            ${escapeHtml(
                                city
                            )}.

                        </div>

                    `;

                    return;

                }


                /* =========================
                   RESULTS FOUND
                ========================== */

                if (searchMessage) {

                    searchMessage.textContent =
                        donors.length +
                        " available donor(s) found.";

                    searchMessage.style.color =
                        "#2e7d32";

                }


                donors.forEach(
                    function (donor) {

                        const groupName =
                            donor.bloodGroup
                                ? donor.bloodGroup.bloodGroup
                                : bloodGroup;


                        const safeName =
                            escapeHtml(
                                donor.name
                            );


                        const safeGroup =
                            escapeHtml(
                                groupName
                            );


                        const safeCity =
                            escapeHtml(
                                donor.city
                            );


                        /*
                           IMPORTANT:
                           Blood group and city are
                           passed directly to the
                           Request Blood popup.
                        */

                        donorResults.innerHTML += `

                            <div class="donor-card">

                                <div class="blood-badge">

                                    ${safeGroup}

                                </div>


                                <h3>

                                    ${safeName}

                                </h3>


                                <p>

                                    📍

                                    <strong>
                                        City:
                                    </strong>

                                    ${safeCity}

                                </p>


                                <span
                                    class="available-badge">

                                    ● Available

                                </span>


                                <button

                                    type="button"

                                    class="request-blood-btn"

                                    onclick="openRequestPopup(
                                        '${escapeHtmlForAttribute(
                                            donor.name
                                        )}',
                                        '${escapeHtmlForAttribute(
                                            groupName
                                        )}',
                                        '${escapeHtmlForAttribute(
                                            donor.city
                                        )}'
                                    )">

                                    🩸 Request Blood

                                </button>

                            </div>

                        `;

                    }
                );

            }

            catch (error) {

                console.error(
                    "Search error:",
                    error
                );


                if (searchMessage) {

                    searchMessage.textContent =
                        "❌ Unable to search donors. Please try again.";

                    searchMessage.style.color =
                        "#c62828";

                }

            }

        }
    );

}


/* =========================================================
   ESCAPE HTML
========================================================= */

function escapeHtml(value) {

    if (
        value === null ||
        value === undefined
    ) {

        return "";

    }


    return String(value)

        .replace(
            /&/g,
            "&amp;"
        )

        .replace(
            /</g,
            "&lt;"
        )

        .replace(
            />/g,
            "&gt;"
        )

        .replace(
            /"/g,
            "&quot;"
        )

        .replace(
            /'/g,
            "&#039;"
        );

}


/* =========================================================
   ESCAPE HTML ATTRIBUTE
========================================================= */

function escapeHtmlForAttribute(
    value
) {

    if (
        value === null ||
        value === undefined
    ) {

        return "";

    }


    return String(value)

        .replace(
            /\\/g,
            "\\\\"
        )

        .replace(
            /'/g,
            "\\'"
        )

        .replace(
            /"/g,
            "&quot;"
        )

        .replace(
            /</g,
            "&lt;"
        )

        .replace(
            />/g,
            "&gt;"
        );

}


/* =========================================================
   BLOOD REQUEST
========================================================= */

/*
   These variables remember which donor
   the user selected.

   The popup does NOT need visible
   Blood Group or City fields.
*/

let selectedRequestDonorName = "";

let selectedRequestBloodGroup = "";

let selectedRequestCity = "";


/* =========================================================
   OPEN REQUEST POPUP
========================================================= */

function openRequestPopup(
    donorName,
    donorBloodGroup,
    donorCity
) {

    const popup =
        document.getElementById(
            "requestPopup"
        );


    if (!popup) {

        console.error(
            "ERROR: requestPopup not found."
        );

        return;

    }


    /* =========================
       SAVE DONOR INFORMATION
    ========================== */

    selectedRequestDonorName =
        donorName || "";


    selectedRequestBloodGroup =
        donorBloodGroup || "";


    selectedRequestCity =
        donorCity || "";


    console.log(
        "Selected donor for blood request:",
        {

            donorName:
                selectedRequestDonorName,

            bloodGroup:
                selectedRequestBloodGroup,

            city:
                selectedRequestCity

        }
    );


    /* =========================
       OPEN POPUP
    ========================== */

    popup.classList.add(
        "show"
    );


    /* =========================
       FIND PATIENT FIELD
    ========================== */

    const patientField =
        findRequestField(
            [
                "patientName",
                "requestPatientName"
            ],
            0
        );


    if (patientField) {

        patientField.focus();

    }

}


/* =========================================================
   CLOSE REQUEST POPUP
========================================================= */

function closeRequestPopup() {

    const popup =
        document.getElementById(
            "requestPopup"
        );


    if (!popup) {

        return;

    }


    popup.classList.remove(
        "show"
    );

}


/* =========================================================
   CLOSE POPUP WHEN CLICKING OUTSIDE
========================================================= */

const requestPopup =
    document.getElementById(
        "requestPopup"
    );


if (requestPopup) {

    requestPopup.addEventListener(
        "click",
        function (event) {

            if (
                event.target ===
                requestPopup
            ) {

                closeRequestPopup();

            }

        }
    );

}


/* =========================================================
   FIND REQUEST FORM
========================================================= */

const requestForm =
    document.getElementById(
        "requestForm"
    );


/* =========================================================
   FIND REQUEST FIELD
========================================================= */

function findRequestField(
    possibleIds,
    position
) {

    /* =========================
       TRY KNOWN IDs
    ========================== */

    for (
        const id
        of possibleIds
    ) {

        const element =
            document.getElementById(
                id
            );


        if (element) {

            return element;

        }

    }


    /* =========================
       TRY FORM POSITION
    ========================== */

    if (requestForm) {

        const fields =
            requestForm.querySelectorAll(
                "input:not([type='hidden']), select, textarea"
            );


        if (
            fields[position]
        ) {

            return fields[position];

        }

    }


    return null;

}


/* =========================================================
   FIND REQUEST MESSAGE
========================================================= */

function findRequestMessage() {

    const possibleIds = [

        "requestMessage",

        "bloodRequestMessage",

        "requestFormMessage"

    ];


    for (
        const id
        of possibleIds
    ) {

        const element =
            document.getElementById(
                id
            );


        if (element) {

            return element;

        }

    }


    if (requestForm) {

        const message =
            requestForm.querySelector(
                ".request-message"
            );


        if (message) {

            return message;

        }

    }


    return null;

}


/* =========================================================
   BLOOD REQUEST SUBMIT
========================================================= */

if (requestForm) {

    requestForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            console.log(
                "Blood request form submitted."
            );


            /* =========================
               FIND ACTUAL FORM FIELDS
            ========================== */

            const patientNameField =
                findRequestField(
                    [
                        "patientName",
                        "requestPatientName"
                    ],
                    0
                );


            const hospitalNameField =
                findRequestField(
                    [
                        "hospitalName",
                        "requestHospitalName"
                    ],
                    1
                );


            const contactNumberField =
                findRequestField(
                    [
                        "contactNumber",
                        "requestContactNumber"
                    ],
                    2
                );


            const urgencyField =
                findRequestField(
                    [
                        "urgency",
                        "requestUrgency"
                    ],
                    3
                );


            const message =
                findRequestMessage();


            /* =========================
               DEBUG INFORMATION
            ========================== */

            console.log(
                "Request form fields:",
                {

                    patientNameField:
                        patientNameField,

                    hospitalNameField:
                        hospitalNameField,

                    contactNumberField:
                        contactNumberField,

                    urgencyField:
                        urgencyField

                }
            );


            /* =========================
               CHECK FORM FIELDS
            ========================== */

            if (
                !patientNameField ||
                !hospitalNameField ||
                !contactNumberField ||
                !urgencyField
            ) {

                console.error(
                    "Request form fields are missing."
                );


                if (message) {

                    message.textContent =
                        "❌ Request form fields are missing.";

                    message.style.color =
                        "#c62828";

                }

                return;

            }


            /* =========================
               READ VALUES
            ========================== */

            const patientName =
                patientNameField.value.trim();


            const hospitalName =
                hospitalNameField.value.trim();


            const contactNumber =
                contactNumberField.value.trim();


            const urgency =
                urgencyField.value;


            /*
               These two values come from
               the selected donor.
            */

            const bloodGroup =
                selectedRequestBloodGroup;


            const city =
                selectedRequestCity;


            console.log(
                "Final blood request data:",
                {

                    patientName:
                        patientName,

                    hospitalName:
                        hospitalName,

                    contactNumber:
                        contactNumber,

                    bloodGroup:
                        bloodGroup,

                    city:
                        city,

                    urgency:
                        urgency

                }
            );


            /* =========================
               VALIDATION
            ========================== */

            if (
                !patientName ||
                !hospitalName ||
                !contactNumber ||
                !bloodGroup ||
                !city ||
                !urgency
            ) {

                if (message) {

                    message.textContent =
                        "❌ Please fill in all required fields.";

                    message.style.color =
                        "#c62828";

                }


                console.error(
                    "Missing blood request data."
                );


                return;

            }


            /* =========================
               PHONE VALIDATION
            ========================== */

            if (
                !/^[0-9]{10}$/.test(
                    contactNumber
                )
            ) {

                if (message) {

                    message.textContent =
                        "❌ Please enter a valid 10-digit contact number.";

                    message.style.color =
                        "#c62828";

                }

                return;

            }


            /* =========================
               CREATE REQUEST DATA
            ========================== */

            const requestData = {

                patientName:
                    patientName,

                hospitalName:
                    hospitalName,

                contactNumber:
                    contactNumber,

                bloodGroup:
                    bloodGroup,

                city:
                    city,

                urgency:
                    urgency

            };


            console.log(
                "Sending blood request:",
                requestData
            );


            /* =========================
               SUBMITTING MESSAGE
            ========================== */

            if (message) {

                message.textContent =
                    "Submitting blood request...";

                message.style.color =
                    "#555";

            }


            /* =========================
               SEND TO BACKEND
            ========================== */

            try {

                const response =
                    await fetch(
                        "/api/blood-requests",
                        {

                            method:
                                "POST",

                            headers:
                                {

                                    "Content-Type":
                                        "application/json"

                                },

                            body:
                                JSON.stringify(
                                    requestData
                                )

                        }
                    );


                console.log(
                    "Response status:",
                    response.status
                );


                /* =========================
                   READ SERVER RESPONSE
                ========================== */

                const responseText =
                    await response.text();


                console.log(
                    "Server response:",
                    responseText
                );


                /* =========================
                   BACKEND ERROR
                ========================== */

                if (!response.ok) {

                    throw new Error(
                        "Server returned " +
                        response.status +
                        ": " +
                        responseText
                    );

                }


                /* =========================
                   SUCCESS
                ========================== */

                if (message) {

                    message.textContent =
                        "✅ Blood request submitted successfully!";

                    message.style.color =
                        "#2e7d32";

                }


                console.log(
                    "Blood request saved successfully."
                );


                /* =========================
                   CLEAR FORM
                ========================== */

                patientNameField.value =
                    "";

                hospitalNameField.value =
                    "";

                contactNumberField.value =
                    "";


                /*
                   Reset select if possible.
                */

                if (
                    urgencyField.tagName ===
                    "SELECT"
                ) {

                    urgencyField.selectedIndex =
                        0;

                }


                /*
                   Clear selected donor
                   only after successful
                   submission.
                */

                selectedRequestDonorName =
                    "";

                selectedRequestBloodGroup =
                    "";

                selectedRequestCity =
                    "";

            }

            catch (error) {

                console.error(
                    "Blood request error:",
                    error
                );


                if (message) {

                    message.textContent =
                        "❌ Unable to submit request. Please try again.";

                    message.style.color =
                        "#c62828";

                }

            }

        }
    );

}
/* =========================================================
   STEP 15.4 - BLOOD REQUEST MANAGEMENT
========================================================= */


/* =========================================================
   LOAD ALL BLOOD REQUESTS
========================================================= */

async function loadBloodRequests() {

    const table =
        document.getElementById(
            "bloodRequestsTable"
        );


    const message =
        document.getElementById(
            "requestManagementMessage"
        );


    if (!table) {

        return;

    }


    table.innerHTML = `

        <tr>

            <td colspan="9">

                Loading blood requests...

            </td>

        </tr>

    `;


    try {

        const response =
            await fetch(
                "/api/blood-requests"
            );


        if (!response.ok) {

            throw new Error(
                "Failed to load blood requests"
            );

        }


        const requests =
            await response.json();


        displayBloodRequests(
            requests
        );

    }

    catch (error) {

        console.error(
            "Blood request loading error:",
            error
        );


        table.innerHTML = `

            <tr>

                <td colspan="9">

                    ❌ Unable to load blood requests.

                </td>

            </tr>

        `;


        if (message) {

            message.textContent =
                "❌ Unable to load blood requests.";

            message.style.color =
                "#c62828";

        }

    }

}


/* =========================================================
   DISPLAY BLOOD REQUESTS
========================================================= */

function displayBloodRequests(
    requests
) {

    const table =
        document.getElementById(
            "bloodRequestsTable"
        );


    if (!table) {

        return;

    }


    table.innerHTML = "";


    /* ==========================================
       NO REQUESTS
    ========================================== */

    if (
        !requests ||
        requests.length === 0
    ) {

        table.innerHTML = `

            <tr>

                <td colspan="9">

                    🩸 No blood requests found.

                </td>

            </tr>

        `;

        return;

    }


    /* ==========================================
       CREATE ROWS
    ========================================== */

    requests.forEach(
        function (request) {

            const status =
                request.status ||
                "PENDING";


            const urgency =
                request.urgency ||
                "N/A";


            const statusClass =
                status.toLowerCase()
                    .replace(
                        /\s+/g,
                        "-"
                    );


            const urgencyClass =
                urgency.toLowerCase();


            table.innerHTML += `

                <tr>

                    <td>
                        ${request.id}
                    </td>


                    <td>
                        ${escapeHtml(
                            request.patientName
                        )}
                    </td>


                    <td>
                        ${escapeHtml(
                            request.hospitalName
                        )}
                    </td>


                    <td>
                        ${escapeHtml(
                            request.contactNumber
                        )}
                    </td>


                    <td>

                        <strong>

                            ${escapeHtml(
                                request.bloodGroup
                            )}

                        </strong>

                    </td>


                    <td>
                        ${escapeHtml(
                            request.city
                        )}
                    </td>


                    <td>

                        <span
                            class="urgency-${urgencyClass}">

                            ${escapeHtml(
                                urgency
                            )}

                        </span>

                    </td>


                    <td>

                        <span
                            class="status-badge status-${statusClass}">

                            ${escapeHtml(
                                status
                            )}

                        </span>

                    </td>


                    <td>

                        <button

                            type="button"

                            class="request-action-btn update-request-btn"

                            onclick="updateBloodRequestStatus(
                                ${request.id}
                            )">

                            🔄 Update

                        </button>


                        <button

                            type="button"

                            class="request-action-btn delete-request-btn"

                            onclick="deleteBloodRequest(
                                ${request.id}
                            )">

                            🗑️ Delete

                        </button>

                    </td>

                </tr>

            `;

        }
    );

}


/* =========================================================
   FILTER BLOOD REQUESTS
========================================================= */

async function filterBloodRequests() {

    const statusSelect =
        document.getElementById(
            "requestStatusFilter"
        );


    if (!statusSelect) {

        return;

    }


    const status =
        statusSelect.value;


    if (!status) {

        loadBloodRequests();

        return;

    }


    try {

        const response =
            await fetch(
                "/api/blood-requests/status/"
                +
                encodeURIComponent(
                    status
                )
            );


        if (!response.ok) {

            throw new Error(
                "Failed to filter requests"
            );

        }


        const requests =
            await response.json();


        displayBloodRequests(
            requests
        );

    }

    catch (error) {

        console.error(
            "Filter error:",
            error
        );

    }

}


/* =========================================================
   UPDATE BLOOD REQUEST STATUS
========================================================= */

async function updateBloodRequestStatus(
    id
) {

    const newStatus =
        prompt(
            "Enter new status:\n\nPENDING\nFULFILLED\nCANCELLED"
        );


    if (!newStatus) {

        return;

    }


    const status =
        newStatus
            .trim()
            .toUpperCase();


    if (
        status !== "PENDING" &&
        status !== "FULFILLED" &&
        status !== "CANCELLED"
    ) {

        alert(
            "Please enter PENDING, FULFILLED, or CANCELLED."
        );

        return;

    }


    try {

        const response =
            await fetch(
                "/api/blood-requests/"
                +
                id
                +
                "/status?status="
                +
                encodeURIComponent(
                    status
                ),
                {

                    method:
                        "PUT"

                }
            );


        if (!response.ok) {

            throw new Error(
                "Failed to update status"
            );

        }


        alert(
            "✅ Blood request status updated successfully!"
        );


        loadBloodRequests();

    }

    catch (error) {

        console.error(
            "Status update error:",
            error
        );


        alert(
            "❌ Failed to update blood request status."
        );

    }

}


/* =========================================================
   DELETE BLOOD REQUEST
========================================================= */

async function deleteBloodRequest(
    id
) {

    const confirmed =
        confirm(
            "Are you sure you want to delete this blood request?"
        );


    if (!confirmed) {

        return;

    }


    try {

        const response =
            await fetch(
                "/api/blood-requests/"
                +
                id,
                {

                    method:
                        "DELETE"

                }
            );


        if (!response.ok) {

            throw new Error(
                "Failed to delete request"
            );

        }


        alert(
            "✅ Blood request deleted successfully!"
        );


        loadBloodRequests();

    }

    catch (error) {

        console.error(
            "Delete error:",
            error
        );


        alert(
            "❌ Failed to delete blood request."
        );

    }

}


/* =========================================================
   LOAD REQUEST MANAGEMENT PAGE
========================================================= */

if (
    document.getElementById(
        "bloodRequestsTable"
    )
) {

    loadBloodRequests();

}