const tree = {
    getAllItems<T>(input: T[], getChildren: (item: T) => T[], conditionFunc: (item: T) => boolean): T[] {
        let data = input.map(x => getChildren(x).filter(conditionFunc)).reduce((a, b) => a.concat(b), []);

        if (data.length === 0) {
            return [];
        }

        return data.concat(tree.getAllItems(data, getChildren, conditionFunc));
    },

    getAllItemsByRoot<T, T1>(rootId: T1, allData: T[], getParentFunc: (item: T) => T1, getIdFunc: (item: T) => T1): T[] {
        let children = allData.filter(x => getParentFunc(x) === rootId);

        if (children.length > 0) {
            const clonedChildren = [...children];
            clonedChildren.forEach(child => {
                children = children.concat(tree.getAllItemsByRoot(getIdFunc(child), allData, getParentFunc, getIdFunc));
            })
        }

        return children;
    }
}

export default tree;