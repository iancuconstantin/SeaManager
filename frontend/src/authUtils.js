export const getBasicAuthHeaders = (user, pass) => {
    const headers = new Headers();
    const credentials = Buffer.from(`${user}:${pass}`).toString('base64');
    headers.append('Authorization', `Basic ${credentials}`);
    return headers;
};

export const getBearerAuthHeaders = () => {
    const token = localStorage.getItem("token");
    const headers = new Headers();
    if(token){
        headers.append('Authorization', `Bearer ${token}`);
    }
    return headers;
};