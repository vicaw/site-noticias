import ApiError from '../models/ApiError';
import Category from '../models/Category';

const getCategories = async (): Promise<Category[]> => {
  const response = await fetch(`http://localhost:8080/api/categories`, {
    next: { revalidate: 1 },
  });
  const data = await response.json();

  if (response.ok) return data as Category[];

  console.log('[CategoryServices] getCategories() Error: ', data.message);
  return [{}] as any;
};

async function checkCategoryBySlug(slug: string) {
  const response = await fetch(`http://localhost:8080/api/categories/slugs/${slug}`, {
    cache: 'force-cache',
    next: { revalidate: 1 },
  });

  const res = await response.json();

  if (!response.ok) {
    console.log('[CategoryServices] checkCategoryBySlug() Error:', res.message);
    const error = new ApiError('Get Comments Count Error', res.message, res.code);

    throw error;
  }
}

const categoryService = {
  getCategories,
  checkCategoryBySlug,
};

export default categoryService;
