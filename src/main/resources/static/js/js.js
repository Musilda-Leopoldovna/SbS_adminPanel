document.addEventListener('DOMContentLoaded', async () => {
    try {
        // Текущий пользователь
        const response = await fetch('/api', {
            method: 'GET',
            headers: {'Accept': 'application/json'},
            credentials: 'include'
        });
        const user = await response.json();
        document.getElementById('username').textContent = user.userLogin;
        document.getElementById('roles').textContent = user.roleNames.join(', ');

        // Боковое меню со ссылками на страницы
        const navLinks = document.getElementById('nav-links');
        const currentPath = window.location.pathname;
        if (user.roleNames.includes('ADMIN')) {
            navLinks.innerHTML += `
                <li class="nav-item">
                    <a href="/admin" class="nav-link ${currentPath === '/admin' ? 'active' : ''}">Администратор</a> 
                </li>`;
        }
        navLinks.innerHTML += `
            <li class="nav-item">
                <a href="/user" class="nav-link ${currentPath === '/user' ? 'active' : ''}">Текущий пользователь</a>
            </li>`;


        // Список пользователей
        if (currentPath === '/admin' && user.roleNames.includes('ADMIN')) {
            const allUsersResponse = await fetch('/api/users', {
                method: 'GET',
                headers: {
                    'Accept': 'application/json'},
                credentials: 'include'
            });

            const users = await allUsersResponse.json();
            const allUsersTbody = document.getElementById('users-data');
            allUsersTbody.innerHTML = ''; // очищаем перед заполнением

            users.forEach(u => {
                const userRow = document.createElement('tr');
                userRow.setAttribute('data-id', u.ID); // для удаления
                const roles = (u.roleNames && u.roleNames.length > 0)
                    ? u.roleNames.join(', ')
                    : 'Права не назначены';
                userRow.innerHTML = `
                    <td>${u.ID}</td>
                    <td>${u.userName}</td>
                    <td>${u.userEmail}</td>
                    <td>${roles}</td>
                    <td>
                        <div class="button-group">
                            <button type="button" class="update_button" 
                                    data-id="${u.ID}" 
                                    data-firstname="${u.userName}" 
                                    data-email="${u.userEmail}" 
                                    data-roles="${roles}" 
                                    data-bs-toggle="modal" 
                                    data-bs-target="#editForm"
                                    title="редактировать">upd</button>
                            <button type="button" class="delete_button" 
                                    data-id="${u.ID}" 
                                    data-firstname="${u.userName}" 
                                    data-email="${u.userEmail}" 
                                    data-bs-toggle="modal" 
                                    data-bs-target="#deleteForm"
                                    title="удалить">del</button>
                        </div>
                    </td>`;
                allUsersTbody.appendChild(userRow);

                //Заполнение модального окна "Редактировать"
                const editBtn = userRow.querySelector('.update_button');
                editBtn.addEventListener('click', () => {
                    const userId = editBtn.getAttribute('data-id');
                    document.getElementById('ID').value = userId;
                    document.getElementById('userFirstName').value = editBtn.getAttribute('data-firstname');
                    document.getElementById('userEmail').value = editBtn.getAttribute('data-email');
                    document.getElementById('roleList').innerText = editBtn.getAttribute('data-roles');
                    document.getElementById('userID').value = userId;
                });
                // Заполнение модального окна "Удалить"
                const delBtn = userRow.querySelector('.delete_button');
                delBtn.addEventListener('click', () => {
                    const userId = delBtn.getAttribute('data-id');
                    document.getElementById('delID').value = userId;
                    document.getElementById('deleteFirstName').value = delBtn.getAttribute('data-firstname');
                    document.getElementById('deleteEmail').value = delBtn.getAttribute('data-email');
                    document.getElementById('deleteId').value = userId;
                });
            });

            // Удаление пользователя
            document.getElementById('confirmDeleteBtn').addEventListener('click', async () => {
                const userId = document.getElementById('deleteId').value;
                try {
                    const deleteResponse = await fetch('/api/users', {
                        method: 'DELETE',
                        headers: {
                            'Content-Type': 'application/json',
                            'Accept': 'application/json'
                        },
                        credentials: 'include',
                        body: JSON.stringify({ ID: Number(userId) })
                    });

                    if (deleteResponse.status === 204) {
                        // находит нужный ID, чтобы именно эта строка была удалена из DOM без перезагрузки страницы
                        const rowToDelete = document.querySelector(`tr[data-id="${userId}"]`);
                        if (rowToDelete) rowToDelete.remove();
                        // закрывает созданный экземпляр модального окна
                        const modalEl = document.getElementById('deleteForm');
                        const modalInstance = bootstrap.Modal.getInstance(modalEl);
                        modalInstance.hide();
                    } else {
                        // ошибка, пришедшая с сервера
                        const errorText = await deleteResponse.text();
                        alert(`Ошибка удаления: ${errorText}`);
                    }
                } catch (error) {
                    // сбой на клиенте или в сети
                    console.error('Ошибка при удалении:', error);
                    alert('Ошибка удаления пользователя');
                }
            });

        } else if (currentPath === '/user') {
            // Заполнение данных таблицы текущего пользователя
            const tbody = document.getElementById('user-data');
            const row = document.createElement('tr');
            row.innerHTML = `
            <td>${user.ID}</td>
            <td>${user.userName}</td>
            <td>${user.userEmail}</td>
            <td>${user.roleNames.join(', ')}</td>
        `;
            tbody.appendChild(row);
        }
    } catch (error) {
    console.error('Ошибка:', error);
    alert('Не удалось загрузить данные пользователя');
}
});