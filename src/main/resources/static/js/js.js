document.addEventListener('DOMContentLoaded', () => {
    //"Редактировать"
    const editButtons = document.querySelectorAll('.update_button');
    editButtons.forEach(button => {
        button.addEventListener('click', () => {
            const userId = button.getAttribute('data-id');
            document.getElementById('ID').value = userId;
            document.getElementById('userFirstName').value = button.getAttribute('data-firstname');
            document.getElementById('userEmail').value = button.getAttribute('data-email');
            document.getElementById('roleList').innerText = button.getAttribute('data-roles');
            document.getElementById('userID').value = userId;
        });
    });
    // "Удалить"
    const deleteButtons = document.querySelectorAll('.delete_button');
    deleteButtons.forEach(button => {
        button.addEventListener('click', () => {
            const userId = button.getAttribute('data-id');
            document.getElementById('delID').value = userId;
            document.getElementById('deleteFirstName').value = button.getAttribute('data-firstname');
            document.getElementById('deleteEmail').value = button.getAttribute('data-email');
            document.getElementById('deleteId').value = userId;
        });
    });
});
