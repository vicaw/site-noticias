'use client';

import Link from 'next/link';
import React from 'react';
import { useScrollPosition } from '../../../hooks/useScrollPosition';
import Menu from './menu';
import { usePathname } from 'next/navigation';
import SearchBar from './searchbar';

function classNames(...classes: String[]) {
  return classes.filter(Boolean).join(' ');
}

function Header() {
  const scrollPosition = useScrollPosition();

  return (
    <header
      className={classNames(
        scrollPosition > 0 ? 'py-2 text-[2.3rem]' : 'py-4 text-[2.5rem]',
        'bg-red-700 fixed top-0 right-0 left-0 transition-spacing duration-200 px-5'
      )}
    >
      <div className="flex items-center max-w-7xl m-auto place-content-between">
        <Menu />
        <Link
          href="/"
          className="absolute right-2/4 translate-x-1/2  font-bold text-white mb-2 -z-10"
        >
          j1
        </Link>
        <SearchBar />
      </div>
    </header>
  );
}

export default Header;
