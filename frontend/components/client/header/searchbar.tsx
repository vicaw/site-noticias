"use client";

import React, { useState, useRef } from "react";
import { MagnifyingGlassIcon } from "@heroicons/react/20/solid";
import { useRouter } from "next/navigation";

function SearchBar() {
  const router = useRouter();

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const query = formData.get("pesquisa") as string;

    router.push(`/busca?q=${query}`);
  };

  return (
    <form onSubmit={handleSubmit}>
      <fieldset className="flex bg-red-800 p-1 items-center gap-1 focuse:bg-white text-sm tracking-tighter font-bold rounded-md focus-within:bg-white transition-all focus-within:text-gray-400 text-white">
        <MagnifyingGlassIcon className="h-5 w-5 t-[-3px] " />
        <input
          className="bg-transparent placeholder:text-white focus:placeholder:text-gray-400 focus:text-black font-bold w-[129px] h-[25px] focus:w-[300px] outline-none transition-all"
          name="pesquisa"
          type="text"
          placeholder="BUSCAR"
        />
      </fieldset>
    </form>
  );
}

export default SearchBar;
