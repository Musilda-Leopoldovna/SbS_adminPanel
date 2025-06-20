// Логика бокового меню

const routesByRole = [
    { path: '/admin', label: 'Администратор', role: 'ADMIN' },
    { path: '/user', label: 'Текущий пользователь', role: 'USER' }
];

export function renderSidebar(user, currentPath) {
    const navLinks = document.getElementById('nav-links');
    if (!navLinks) return;

    navLinks.innerHTML = '';

    routesByRole.forEach(({ path, label, role }) => {
        const hasAccess = user.roleNames.includes(role) || (role === 'USER' && user.roleNames.includes('ADMIN'));
        if (hasAccess) {
            const isActive = currentPath === path ? 'active' : '';
            navLinks.innerHTML += `
                <li class="nav-item">
                    <a href="${path}" class="nav-link ${isActive}">${label}</a>
                </li>`;
        }
    });
}
