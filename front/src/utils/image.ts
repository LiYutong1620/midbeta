export const getFullUrl = (url: string): string => {
  if (!url) return '';
  if (url.startsWith('http')) return url;
  return (import.meta.env.VITE_APP_BASE_API || 'http://localhost:8888') + url;
};