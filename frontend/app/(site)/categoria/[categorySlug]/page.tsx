import { notFound } from 'next/navigation';
import AllArticlesWrapper from '../../../../components/client/articlesfeed/allarticleswrapper';
import categoryService from '../../../../services/CategoryServices';

type PageProps = {
  params: {
    categorySlug: string;
  };
};

async function Category({ params: { categorySlug } }: PageProps) {
  console.log(categorySlug);
  try {
    await categoryService.checkCategoryBySlug(categorySlug);
    return (
      <main className="container mx-auto px-10 mb-8 pt-28">
        <AllArticlesWrapper category={categorySlug} />
      </main>
    );
  } catch (error) {
    return notFound();
  }
}

export default Category;
