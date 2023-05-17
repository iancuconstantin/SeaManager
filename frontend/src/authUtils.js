export const getAuthHeaders = () => {
    const headers = new Headers();
    const username = 'user';
    const password = 'abcD1234';
    const credentials = Buffer.from(`${username}:${password}`).toString('base64');
    headers.append('Authorization', `Basic ${credentials}`);
    return headers;
};