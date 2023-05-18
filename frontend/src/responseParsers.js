export const parseTextResponse = async (response) => {
    const text = await response.text();
    return text;
};

export const parseJsonResponse = async (response) => {
    const json = await response.json();
    return json;
}