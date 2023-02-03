import React from "react";

function ArticleCardSkeleton() {
  return (
    <div className="grid grid-cols-12 gap-4 pt-8">
      <span className="col-span-4 bg-gray-300 h-[10.5vw] rounded" />
      <div className="flex flex-col col-span-8 gap-2">
        <span className="bg-gray-300 h-4 my-1 w-32 rounded" />
        <span className="bg-gray-300 h-6 w-96 my-1 rounded" />

        <div>
          <span className="block bg-gray-300 h-4 w-100 my-1 rounded" />
          <span className="block bg-gray-300 h-4 w-64 my-1 rounded" />
        </div>
        <span className="block bg-gray-300 h-3 w-44 my-[2px] rounded" />
      </div>
    </div>
  );
}

export default ArticleCardSkeleton;
