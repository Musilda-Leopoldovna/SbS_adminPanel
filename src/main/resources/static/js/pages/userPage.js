// Логика шаблона `/user`

export function initUserPage(user) {
    const userDataBody = document.getElementById('user-data');
    if (!userDataBody) return;

    const row = document.createElement('tr');
    row.innerHTML = `
        <td>${user.ID}</td>
        <td>${user.userName}</td>
        <td>${user.userEmail}</td>
        <td>${user.roleNames.join(', ')}</td>
    `;

    userDataBody.appendChild(row);
}
