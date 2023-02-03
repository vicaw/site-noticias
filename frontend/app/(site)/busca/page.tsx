"use client";

import { useSearchParams } from "next/navigation";
import { useRouter } from "next/router";
import SearchArticlesWrapper from "../../../components/client/search/searcharticleswrapper";

function Busca() {
  return (
    <main className="container mx-auto px-10 mb-8 pt-28">
      <SearchArticlesWrapper />
    </main>
  );
}

export default Busca;
