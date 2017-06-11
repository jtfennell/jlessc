/**
 * MIT License (MIT)
 *
 * Copyright (c) 2014 - 2015 Volker Berlin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * UT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author Volker Berlin
 * @license: The MIT license <http://opensource.org/licenses/MIT>
 */
package com.inet.lib.less;

/**
 * A CSS rule that start with an @ character
 */
class CssAtRule extends LessObject implements Formattable {

    private final String css;

    /**
     * Create CSS at-rule that have no special handling. Known CSS at rules are @charset, @document, @font-face,
     * @import, @keyframes, @media, @namespace, @page, @supports
     * 
     * @param reader
     *            the reader with parse position
     * @param css
     *            the content of the rule
     */
    public CssAtRule( LessObject reader, String css ) {
        super( reader );
        this.css = css;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getType() {
        return CSS_AT_RULE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void appendTo( CssFormatter formatter ) {
        if( css.startsWith( "@charset" ) ) {
            if( formatter.isCharsetDirective() ) {
                return; // write charset only once
            }
            formatter.setCharsetDirective();
            formatter = formatter.getHeader(); 
        } else if( css.startsWith( "@import" ) ) {
            formatter = formatter.getHeader();
        }
        formatter.getOutput();
        SelectorUtils.appendToWithPlaceHolder( formatter, css, 1, false, this );
        formatter.newline();
    }
}
