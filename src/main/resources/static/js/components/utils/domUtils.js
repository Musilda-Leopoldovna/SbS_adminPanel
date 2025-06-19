// Утилиты работы с DOM

export function removeRowById(userId) {
    const row = document.querySelector(`tr[data-id="${userId}"]`);
    if (row) row.remove();
}

export function updateRowById(userDto) {
    const tableBody = document.getElementById('users-data');
    const row = tableBody.querySelector(`tr[data-id="${userDto.ID}"]`);
    if (!row) {
        console.warn(`Строка с ID ${userDto.ID} не найдена`);
        return;
    }

    const cells = row.querySelectorAll('td');
    if (cells.length < 4) {
        console.warn('Недостаточно ячеек в строке');
        return;
    }
    cells[0].textContent = userDto.ID;
    cells[1].textContent = userDto.userName;
    cells[2].textContent = userDto.userEmail;
    cells[3].textContent = userDto.roleNames.length > 0
        ? userDto.roleNames.join(', ')
        : 'Права не назначены';
}

export function closeModal(modalId) {
    const modalElement = document.getElementById(modalId);
    if (!modalElement) return;
    const modalInstance = bootstrap.Modal.getInstance(modalElement)
        || new bootstrap.Modal(modalElement);
    modalInstance.hide();
}
