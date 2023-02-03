import {
  Cog6ToothIcon,
  DocumentTextIcon,
  EnvelopeIcon,
  NewspaperIcon,
} from '@heroicons/react/24/outline';
import Link from 'next/link';

type menuItemType = {
  name: string;
  svg: JSX.Element;
};

const menuItems = [
  {
    name: 'Messages',
    svg: <EnvelopeIcon className="h-6 w-6" />,
  },
];

const MenuItem = (item: menuItemType) => (
  <a
    key={item.name}
    href="#"
    className="inline-flex items-center justify-center py-3 hover:text-gray-400 hover:bg-gray-700 focus:text-gray-400 focus:bg-gray-700 rounded-lg"
  >
    <span className="sr-only">{item.name}</span>
    {item.svg}
  </a>
);

export default function PainelAside() {
  return (
    <aside className="sm:flex sm:flex-col h-screen sticky top-0">
      <Link
        href="/"
        className="inline-flex items-center justify-center h-20 w-20 font-bold text-white text-[2.5rem] bg-red-600 hover:bg-red-500 focus:bg-red-500"
      >
        j1
      </Link>
      <div className="flex-grow flex flex-col justify-between text-gray-500 bg-gray-800">
        <nav className="flex flex-col mx-4 my-6 space-y-4">
          <a
            href="#"
            className="inline-flex items-center justify-center py-3 text-red-600 bg-white rounded-lg"
          >
            <span className="sr-only">Dashboard</span>
            <NewspaperIcon className="h-6 " />
          </a>

          {menuItems.map((item) => MenuItem(item))}
        </nav>
        <div className="inline-flex items-center justify-center h-20 w-20 border-t border-gray-700">
          <button className="p-3 hover:text-gray-400 hover:bg-gray-700 focus:text-gray-400 focus:bg-gray-700 rounded-lg">
            <span className="sr-only">Settings</span>
            <Cog6ToothIcon className="h-6 w-6" />
          </button>
        </div>
      </div>
    </aside>
  );
}
