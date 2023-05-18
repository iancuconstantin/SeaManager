import {getBasicAuthHeaders, getBearerAuthHeaders} from "./authUtils";

export const fetchBasicAuth = async (url, method, user, pass) => {
    const headers = getBasicAuthHeaders(user, pass);
    const response = await fetch(url, {
        method: method,
        headers: headers
    });
    return response;
};

export const fetchBearerAuth = async (url, method) => {
    console.log("IN FETCH BEARER")
    const headers = getBearerAuthHeaders();
    const response = await fetch(url, {
        method: method,
        headers: headers
    });
    return response;
};

export const fetchBearerAuthWithBody = async (url, method, body) => {
    console.log("IN FETCH BEARER WITH BODY")
    const headers = getBearerAuthHeaders();
    const response = await fetch(url, {
        method: method,
        headers: headers,
        body: JSON.stringify(body)
    });
    return response;
};