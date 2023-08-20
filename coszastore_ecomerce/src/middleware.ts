import { NextRequest, NextResponse } from "next/server";
import { TOKEN_USER_MOVIE } from "./app/utils/config";

export function middleware(request: NextRequest) {
    const cookie = request.cookies.get(TOKEN_USER_MOVIE)?.value;
    if (cookie === undefined) {
        return NextResponse.redirect(new URL('/auth/signIn', request.url))
    }
}

export const config = {
    matcher: ['/((?!_next/static|favicon.ico|login|).*)', '/about/:path*', '/profile/:path*'],
}