// Отрисовка данных авторизованного пользователя в header

export function renderHeader(user) {

    document.getElementById('username').textContent = user.userName;
    document.getElementById('roles').textContent = user.roleNames.join(', ');
}
