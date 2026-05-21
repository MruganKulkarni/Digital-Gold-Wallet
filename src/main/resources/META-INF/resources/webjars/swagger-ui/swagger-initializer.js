window.onload = function () {

    /*
     * ============================================================
     * CURRENT USER ROLE
     * ============================================================
     */
    let currentRole = "";

    /*
     * ============================================================
     * INITIALIZE SWAGGER UI
     * ============================================================
     */
    window.ui = SwaggerUIBundle({

        url: "/v3/api-docs",

        dom_id: '#swagger-ui',

        deepLinking: true,

        presets: [
            SwaggerUIBundle.presets.apis,
            SwaggerUIStandalonePreset
        ],

        plugins: [
            SwaggerUIBundle.plugins.DownloadUrl
        ],

        layout: "StandaloneLayout"
    });

    /*
     * ============================================================
     * GET CURRENT LOGGED-IN USER ROLE
     * ============================================================
     */
    fetch("/api/auth/me")

        .then(response => response.json())

        .then(data => {

            /*
             * Example:
             * ROLE_PAYMENT_DEV
             */
            currentRole = data.role;

            /*
             * Apply restrictions
             */
            setTimeout(
                applyRoleRestrictions,
                1500
            );
        });

    /*
     * ============================================================
     * ADD LOGOUT BUTTON
     * ============================================================
     */
    setTimeout(() => {

        const topbar =
            document.querySelector(".topbar");

        if (
            topbar &&
            !document.getElementById(
                "custom-logout-btn"
            )
        ) {

            /*
             * Create button
             */
            const logoutBtn =
                document.createElement("button");

            logoutBtn.id =
                "custom-logout-btn";

            logoutBtn.innerHTML =
                "Logout";

            /*
             * Styling
             */
            logoutBtn.style.marginLeft =
                "20px";

            logoutBtn.style.padding =
                "6px 14px";

            logoutBtn.style.backgroundColor =
                "#d9534f";

            logoutBtn.style.color =
                "white";

            logoutBtn.style.border =
                "none";

            logoutBtn.style.borderRadius =
                "4px";

            logoutBtn.style.cursor =
                "pointer";

            logoutBtn.style.fontSize =
                "14px";

            /*
             * Logout action
             */
            logoutBtn.onclick = function () {

                fetch("/logout", {

                    method: "POST",

                    credentials: "same-origin"

                }).then(() => {

                    /*
                     * Redirect back to Swagger
                     */
                    window.location.href =
                        "/swagger-ui/index.html";
                });
            };

            /*
             * Add button
             */
            topbar.appendChild(logoutBtn);
        }

    }, 1500);

    /*
     * ============================================================
     * APPLY ROLE-BASED ACCESS RESTRICTIONS
     * ============================================================
     */
    function applyRoleRestrictions() {

        /*
         * Get all sections
         */
        const tags =
            document.querySelectorAll(".opblock-tag-section");

        tags.forEach(tagSection => {

            const heading =
                tagSection.querySelector(".opblock-tag");

            if (!heading) return;

            const headingText =
                heading.innerText.toLowerCase();

            /*
             * ====================================================
             * DETERMINE ACCESS
             * ====================================================
             */
            let allowed = false;

            /*
             * REPORT APIs
             */
            if (
                headingText.includes("report")
            ) {

                allowed = true;
            }

            /*
             * PAYMENT
             */
            else if (
                currentRole === "ROLE_PAYMENT_DEV" &&
                (
                    headingText.includes("wallet") ||
                    headingText.includes("payment")
                )
            ) {

                allowed = true;
            }

            /*
             * USER
             */
            else if (
                currentRole === "ROLE_USER_DEV" &&
                (
                    headingText.includes("user") ||
                    headingText.includes("address")
                )
            ) {

                allowed = true;
            }

            /*
             * VENDOR
             */
            else if (
                currentRole === "ROLE_VENDOR_DEV" &&
                (
                    headingText.includes("vendor") ||
                    headingText.includes("branch")
                )
            ) {

                allowed = true;
            }

            /*
             * GOLD
             */
            else if (
                currentRole === "ROLE_GOLD_DEV" &&
                (
                    headingText.includes("gold") ||
                    headingText.includes("transaction")
                ) &&
                !headingText.includes("vendor") &&
                !headingText.includes("branch")
            ) {

                allowed = true;
            }

            /*
             * ====================================================
             * BLOCK INACCESSIBLE APIs
             * ====================================================
             */
            if (!allowed) {

                /*
                 * Dim heading
                 */
                heading.style.opacity =
                    "0.5";

                /*
                 * Disable heading click
                 */
                heading.style.pointerEvents =
                    "none";

                /*
                 * APIs
                 */
                tagSection.querySelectorAll(".opblock")
                    .forEach(api => {

                        /*
                         * Dim API
                         */
                        api.style.opacity =
                            "0.35";

                        /*
                         * Disable interactions
                         */
                        api.style.pointerEvents =
                            "none";

                        /*
                         * Cursor
                         */
                        api.style.cursor =
                            "not-allowed";

                        /*
                         * Add ACCESS DENIED label
                         */
                        const lock =
                            document.createElement("div");

                        lock.innerHTML =
                            "🔒 ACCESS DENIED";

                        lock.style.position =
                            "absolute";

                        lock.style.top =
                            "10px";

                        lock.style.right =
                            "15px";

                        lock.style.fontSize =
                            "12px";

                        lock.style.color =
                            "red";

                        lock.style.fontWeight =
                            "bold";

                        api.style.position =
                            "relative";

                        api.appendChild(lock);
                    });
            }
        });
    }
};