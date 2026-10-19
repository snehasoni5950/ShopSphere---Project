// ShopSphere Frontend Interactivity
document.addEventListener('DOMContentLoaded', () => {
    // Auto-dismiss alerts after 4 seconds
    const alerts = document.querySelectorAll('.alert-dismissible');
    alerts.forEach(alert => {
        setTimeout(() => {
            const bsAlert = bootstrap.Alert.getOrCreateInstance(alert);
            if (bsAlert) bsAlert.close();
        }, 4000);
    });

    // Quantity selector buttons
    document.querySelectorAll('.qty-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const input = btn.closest('.input-group').querySelector('.qty-input');
            let current = parseInt(input.value) || 1;
            if (btn.classList.contains('qty-plus')) {
                input.value = current + 1;
            } else if (btn.classList.contains('qty-minus') && current > 1) {
                input.value = current - 1;
            }
        });
    });
});
