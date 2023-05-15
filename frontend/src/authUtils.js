export const getAuthHeaders = () => {
    const headers = new Headers();
    const username = 'admin';
    const password = 'admin123';
    const credentials = Buffer.from(`${username}:${password}`).toString('base64');
    headers.append('Authorization', `Basic ${credentials}`);
    return headers;
};