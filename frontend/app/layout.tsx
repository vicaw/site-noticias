import '../styles/globals.css';
import Providers from './providers';

export default function SiteLayout({ children }: { children: React.ReactNode }) {
  return (
    <html>
      <head />
      <body>
        <Providers>{children}</Providers>
      </body>
    </html>
  );
}
