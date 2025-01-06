document.addEventListener('DOMContentLoaded', () => {
    //"Редактировать"
    const editButtons = document.querySelectorAll('.update_button');
    editButtons.forEach(button => {
        button.addEventListener('click', () => {
            const userId = button.getAttribute('data-id');
            const firstName = button.getAttribute('data-firstname');
            const email = button.getAttribute('data-email');
            document.getElementById('ID').value = userId;
            document.getElementById('userFirstName').value = firstName;
            document.getElementById('userEmail').value = email;
            document.getElementById('userId').value = userId;
        });
    });
    // "Удалить"
    const deleteButtons = document.querySelectorAll('.delete_button');
    deleteButtons.forEach(button => {
        button.addEventListener('click', () => {
            const userId = button.getAttribute('data-id');
            const firstName = button.getAttribute('data-firstname');
            const email = button.getAttribute('data-email');
            document.getElementById('delID').value = userId;
            document.getElementById('deleteFirstName').value = firstName;
            document.getElementById('deleteEmail').value = email;
            document.getElementById('deleteId').value = userId;
        });
    });
});
