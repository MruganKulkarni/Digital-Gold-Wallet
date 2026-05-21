// custom script to color swagger UI endpoints based on access

window.addEventListener('load', function () {
    setTimeout(async function () {
        try {
            const resp = await fetch('/api/auth/me');
            if (!resp.ok) return;
            const roles = await resp.json();
            
            // map roles to swagger tags
            const roleToTags = {
                'ROLE_PAYMENT_DEV': ['Wallet Controller', 'Payment Controller'],
                'ROLE_USER_DEV': ['user-controller', 'address-controller'],
                'ROLE_VENDOR_DEV': ['Vendor Management', 'Vendor Branch Management'],
                'ROLE_GOLD_DEV': ['Virtual Gold', 'Physical Gold', 'Transaction History']
            };

            let accessibleTags = ['Reports']; // reports are public
            roles.forEach(role => {
                if (roleToTags[role]) {
                    accessibleTags = accessibleTags.concat(roleToTags[role]);
                }
            });

            console.log("User Roles:", roles);
            console.log("Accessible Tags:", accessibleTags);

            // Swagger UI loads asynchronously, so we observe the DOM for changes
            const observer = new MutationObserver((mutations, obs) => {
                const opblocks = document.querySelectorAll('.opblock-tag-section');
                if (opblocks.length > 0) {
                    opblocks.forEach(section => {
                        const tagTitle = section.querySelector('.opblock-tag').getAttribute('data-tag');
                        if (tagTitle) {
                            if (!accessibleTags.includes(tagTitle)) {
                                section.style.opacity = '0.4';
                                section.style.filter = 'grayscale(100%)';
                                // disable clicking on the operations
                                const headers = section.querySelectorAll('.opblock-summary');
                                headers.forEach(h => {
                                    h.style.pointerEvents = 'none';
                                    h.style.cursor = 'not-allowed';
                                });
                                // Add a lock icon/text
                                const titleEl = section.querySelector('.opblock-tag');
                                if (titleEl && !titleEl.innerHTML.includes('🔒')) {
                                    titleEl.innerHTML += ' <span style="font-size:0.8em;color:#999;float:right;margin-right:20px;">🔒 No Access</span>';
                                }
                            } else {
                                section.style.opacity = '1';
                                section.style.filter = 'none';
                            }
                        }
                    });
                }
            });

            observer.observe(document.body, { childList: true, subtree: true });

        } catch (err) {
            console.error("Failed to fetch roles for swagger UI", err);
        }
    }, 1000); // Wait for swagger UI to initialize
});
