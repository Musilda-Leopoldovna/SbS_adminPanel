document.addEventListener('DOMContentLoaded', async () => {
    try {
        const response = await fetch('/api', {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            },
            credentials: 'include'
        });

        const user = await response.json();

        // Данные текущего пользователя
        document.getElementById('username').textContent = user.userLogin;
        document.getElementById('roles').textContent = user.roleNames.join(', ');

        // Заполнение данных таблицы
        const tbody = document.getElementById('user-data');
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${user.ID}</td>
            <td>${user.userName}</td>
            <td>${user.userEmail}</td>
            <td>${user.roleNames.join(', ')}</td>
        `;
        tbody.appendChild(row);

        // Боковое меню со ссылками на страницы: текущего пользователя и администратора (в зависимости от доступа)
        const navLinks = document.getElementById('nav-links');
        if (user.roleNames.includes('ADMIN')) {
            navLinks.innerHTML += `<li class="nav-item">
                <a href="/admin" class="nav-link">Администратор</a>
            </li>`;
        }
        navLinks.innerHTML += `<li>
            <a href="/user" class="nav-link active" aria-current="page">Текущий пользователь</a>
        </li>`;

    } catch (error) {
        console.error('Ошибка:', error);
        alert('Не удалось загрузить данные пользователя');
    }
});
