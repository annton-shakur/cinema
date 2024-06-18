document.addEventListener("DOMContentLoaded", function() {
    const descriptions = document.querySelectorAll('.actor-description');
    descriptions.forEach(desc => {
        const words = desc.textContent.split(' ');
        if (words.length > 10) {
            desc.textContent = words.slice(0, 10).join(' ') + '...';
        }
    });
});
